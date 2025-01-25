package com.project.java.service.Momo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.project.java.config.Environment;
import com.project.java.enums.Language;
import com.project.java.exception.Momo.MoMoException;
import com.project.java.model.Momo.DeleteTokenRequest;
import com.project.java.model.Momo.DeleteTokenResponse;
import com.project.java.model.Momo.HttpResponse;
import com.project.java.utils.Encoder;
import com.project.java.utils.Parameter;

public class DeleteToken extends AbstractProcess<DeleteTokenRequest, DeleteTokenResponse> {
	
	private static final Logger log = LoggerFactory.getLogger(DeleteToken.class);
	
    public DeleteToken(Environment environment) {
        super(environment);
    }

    public static DeleteTokenResponse process(Environment env, String requestId, String orderId, String partnerClientId, String token) {
        try {
            DeleteToken m2Processor = new DeleteToken(env);

            DeleteTokenRequest request = m2Processor.createDeleteTokenRequest(orderId, requestId, partnerClientId, token);
            DeleteTokenResponse response = m2Processor.execute(request);

            return response;
        } catch (Exception exception) {
        	log.error("[DeleteTokenProcess] "+ exception);
        }
        return null;
    }

    @Override
    public DeleteTokenResponse execute(DeleteTokenRequest request) throws MoMoException {
        try {

            String payload = getGson().toJson(request, DeleteTokenRequest.class);

            HttpResponse response = execute.sendToMoMo(environment.getMomoEndpoint().getTokenDeleteUrl(), payload);

            if (response.getStatus() != 200) {
                throw new MoMoException("[DeleteTokenResponse] [" + request.getOrderId() + "] -> Error API");
            }

            System.out.println("uweryei7rye8wyreow8: "+ response.getData());

            DeleteTokenResponse deleteTokenResponse = getGson().fromJson(response.getData(), DeleteTokenResponse.class);
            String responserawData = Parameter.REQUEST_ID + "=" +
                    "&" + Parameter.ORDER_ID + "=" + deleteTokenResponse.getOrderId() +
                    "&" + Parameter.MESSAGE + "=" + deleteTokenResponse.getMessage() +
                    "&" + Parameter.RESULT_CODE + "=" + deleteTokenResponse.getResultCode();

            log.info("[DeleteTokenResponse] rawData: " + responserawData);

            return deleteTokenResponse;

        } catch (Exception exception) {
        	log.error("[DeleteTokenResponse] "+ exception);
            throw new IllegalArgumentException("Invalid params capture MoMo Request");
        }
    }

    public DeleteTokenRequest createDeleteTokenRequest(String orderId, String requestId, String partnerClientId, String token) {
        try {
            String requestRawData = new StringBuilder()
                    .append(Parameter.ACCESS_KEY).append("=").append(partnerInfo.getAccessKey()).append("&")
                    .append(Parameter.ORDER_ID).append("=").append(orderId).append("&")
                    .append(Parameter.PARTNER_CLIENT_ID).append("=").append(partnerClientId).append("&")
                    .append(Parameter.PARTNER_CODE).append("=").append(partnerInfo.getPartnerCode()).append("&")
                    .append(Parameter.REQUEST_ID).append("=").append(requestId).append("&")
                    .append(Parameter.TOKEN).append("=").append(token)
                    .toString();

            String signRequest = Encoder.signHmacSHA256(requestRawData, partnerInfo.getSecretKey());
            log.debug("[DeleteTokenRequest] rawData: " + requestRawData + ", [Signature] -> " + signRequest);

            return new DeleteTokenRequest(partnerInfo.getPartnerCode(), orderId, requestId, Language.EN, partnerClientId, token, signRequest);
        } catch (Exception e) {
        	log.error("[DeleteTokenRequest] "+ e);
        }

        return null;
    }
}
