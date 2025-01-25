package com.project.java.service.Momo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.project.java.config.Environment;
import com.project.java.enums.Language;
import com.project.java.exception.Momo.MoMoException;
import com.project.java.model.Momo.BindingTokenRequest;
import com.project.java.model.Momo.BindingTokenResponse;
import com.project.java.model.Momo.HttpResponse;
import com.project.java.utils.Parameter;
import com.project.java.utils.Encoder;

public class BindingToken extends AbstractProcess<BindingTokenRequest, BindingTokenResponse> {
	
	private static final Logger log = LoggerFactory.getLogger(BindingToken.class);
	
    public BindingToken(Environment environment) {
        super(environment);
    }

    public static BindingTokenResponse process(Environment env, String orderId, String requestId, String partnerClientId, String callbackToken) {
        try {
            BindingToken m2Processor = new BindingToken(env);

            BindingTokenRequest request = m2Processor.createBindingTokenRequest(orderId, requestId, partnerClientId, callbackToken);
            BindingTokenResponse bindingTokenResponse = m2Processor.execute(request);

            return bindingTokenResponse;
        } catch (Exception exception) {
        	log.error("[BindingTokenProcess] "+ exception);
        }
        return null;
    }

    @Override
    public BindingTokenResponse execute(BindingTokenRequest request) throws MoMoException {
        try {

            String payload = getGson().toJson(request, BindingTokenRequest.class);

            HttpResponse response = execute.sendToMoMo(environment.getMomoEndpoint().getTokenBindUrl(), payload);

            if (response.getStatus() != 200) {
                throw new MoMoException("[BindingTokenResponse] [" + request.getOrderId() + "] -> Error API");
            }

            System.out.println("uweryei7rye8wyreow8: "+ response.getData());

            BindingTokenResponse BindingTokenResponse = getGson().fromJson(response.getData(), BindingTokenResponse.class);
            String responserawData = Parameter.REQUEST_ID + "=" + BindingTokenResponse.getRequestId() +
                    "&" + Parameter.ORDER_ID + "=" + BindingTokenResponse.getOrderId() +
                    "&" + Parameter.MESSAGE + "=" + BindingTokenResponse.getMessage() +
                    "&" + Parameter.RESULT_CODE + "=" + BindingTokenResponse.getResultCode();

            log.info("[BindingTokenResponse] rawData: " + responserawData);

            return BindingTokenResponse;

        } catch (Exception exception) {
        	log.error("[BindingTokenResponse] "+ exception);
            throw new IllegalArgumentException("Invalid params capture MoMo Request");
        }
    }

    public BindingTokenRequest createBindingTokenRequest(String orderId, String requestId, String partnerClientId, String callbackToken) {

        try {
            String requestRawData = new StringBuilder()
                    .append(Parameter.ACCESS_KEY).append("=").append(partnerInfo.getAccessKey()).append("&")
                    .append(Parameter.CALLBACK_TOKEN).append("=").append(callbackToken).append("&")
                    .append(Parameter.ORDER_ID).append("=").append(orderId).append("&")
                    .append(Parameter.PARTNER_CLIENT_ID).append("=").append(partnerClientId).append("&")
                    .append(Parameter.PARTNER_CODE).append("=").append(partnerInfo.getPartnerCode()).append("&")
                    .append(Parameter.REQUEST_ID).append("=").append(requestId)
                    .toString();

            String signRequest = Encoder.signHmacSHA256(requestRawData, partnerInfo.getSecretKey());
            log.debug("[BindingTokenRequest] rawData: " + requestRawData + ", [Signature] -> " + signRequest);

            return new BindingTokenRequest(partnerInfo.getPartnerCode(), orderId, requestId, Language.EN, partnerClientId, callbackToken, signRequest);
        } catch (Exception e) {
        	log.error("[BindingTokenResponse] "+ e);
        }

        return null;
    }

}
