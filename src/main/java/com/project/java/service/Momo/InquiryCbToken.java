package com.project.java.service.Momo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.project.java.config.Environment;
import com.project.java.enums.Language;
import com.project.java.exception.Momo.MoMoException;
import com.project.java.model.Momo.CbTokenInquiryRequest;
import com.project.java.model.Momo.CbTokenInquiryResponse;
import com.project.java.model.Momo.HttpResponse;
import com.project.java.utils.Encoder;
import com.project.java.utils.Parameter;

public class InquiryCbToken extends AbstractProcess<CbTokenInquiryRequest, CbTokenInquiryResponse> {
	
	private static final Logger LogUtils = LoggerFactory.getLogger(InquiryCbToken.class);
	
    public InquiryCbToken(Environment environment) {
        super(environment);
    }

    public static CbTokenInquiryResponse process(Environment env, String orderId, String requestId, String partnerClientId) throws Exception {
        try {
            InquiryCbToken m2Processor = new InquiryCbToken(env);

            CbTokenInquiryRequest request = m2Processor.createInquiryTokenRequest(orderId, requestId, partnerClientId);
            CbTokenInquiryResponse cbTokenInquiryResponse = m2Processor.execute(request);

            return cbTokenInquiryResponse;
        } catch (Exception exception) {
            LogUtils.error("[TokenInquiryProcess] "+ exception);
        }
        return null;
    }

    @Override
    public CbTokenInquiryResponse execute(CbTokenInquiryRequest request) throws MoMoException {
        try {

            String payload = getGson().toJson(request, CbTokenInquiryRequest.class);

            HttpResponse response = execute.sendToMoMo(environment.getMomoEndpoint().getCbTokenInquiryUrl(), payload);

            if (response.getStatus() != 200) {
                throw new MoMoException("[CbTokenInquiryResponse] [" + request.getOrderId() + "] -> Error API");
            }

            System.out.println("uweryei7rye8wyreow8: "+ response.getData());

            CbTokenInquiryResponse cbTokenInquiryResponse = getGson().fromJson(response.getData(), CbTokenInquiryResponse.class);
            String responserawData = Parameter.REQUEST_ID + "=" + cbTokenInquiryResponse.getRequestId() +
                    "&" + Parameter.ORDER_ID + "=" + cbTokenInquiryResponse.getOrderId() +
                    "&" + Parameter.MESSAGE + "=" + cbTokenInquiryResponse.getMessage() +
                    "&" + Parameter.RESULT_CODE + "=" + cbTokenInquiryResponse.getResultCode();

            LogUtils.info("[CbTokenInquiryResponse] rawData: " + responserawData);

            return cbTokenInquiryResponse;

        } catch (Exception exception) {
            LogUtils.error("[CbTokenInquiryResponse] "+ exception);
            throw new IllegalArgumentException("Invalid params capture MoMo Request");
        }
    }

    public CbTokenInquiryRequest createInquiryTokenRequest(String orderId, String requestId, String partnerClientId) {
        try {
            String requestRawData = new StringBuilder()
                    .append(Parameter.ACCESS_KEY).append("=").append(partnerInfo.getAccessKey()).append("&")
                    .append(Parameter.ORDER_ID).append("=").append(orderId).append("&")
                    .append(Parameter.PARTNER_CLIENT_ID).append("=").append(partnerClientId).append("&")
                    .append(Parameter.PARTNER_CODE).append("=").append(partnerInfo.getPartnerCode()).append("&")
                    .append(Parameter.REQUEST_ID).append("=").append(requestId)
                    .toString();

            String signRequest = Encoder.signHmacSHA256(requestRawData, partnerInfo.getSecretKey());
            LogUtils.debug("[TokenInquiryRequest] rawData: " + requestRawData + ", [Signature] -> " + signRequest);

            return new CbTokenInquiryRequest(partnerInfo.getPartnerCode(), orderId, requestId, Language.EN, partnerClientId, signRequest);
        } catch (Exception e) {
            LogUtils.error("[TokenInquiryResponse] "+ e);
        }

        return null;
    }
}