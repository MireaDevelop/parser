package org.Sms.Services.SmSAero;

import com.google.gson.*;

import java.lang.reflect.Type;



/**
 * Created by Вадим on 04.05.2016.
 */
public class SmsAeroDeserializer implements JsonDeserializer<Response> {


        @Override
        public Response deserialize(final JsonElement json, final Type typeOfT, final JsonDeserializationContext context)
                throws JsonParseException {

            String result ="";
            String id = "";
            String balance = "";
            String reason = "";
            String Direct_channel ="";
            String Digital_channel="";

            final JsonObject jsonObject = json.getAsJsonObject();


            JsonElement jsonResult = jsonObject.get("result");
             if (jsonResult != null ) {
                 result = jsonResult.getAsString();
             }
            final JsonElement jsonId = jsonObject.get("id");
             if (jsonId != null) {
                 id = jsonId.getAsString();
             }

            final  JsonElement jsonBalance = jsonObject.get("balance");
             if (jsonBalance != null) {
                 balance = jsonBalance.getAsString();
             }

            final JsonElement jsonReason = jsonObject.get("reason");
             if (jsonReason != null) {
                if (jsonReason.isJsonPrimitive()) {
                    reason = jsonReason.getAsString();
                } else if (jsonReason.isJsonObject()) {
                    JsonObject jsonReasonObject = (JsonObject) jsonReason;

                    JsonElement jsonDirect_channel = jsonReasonObject.get("Direct channel");
                    Direct_channel = jsonDirect_channel.getAsString();

                    JsonElement jsonDigital_channel = jsonReasonObject.get("Digital channel");
                    Digital_channel = jsonDigital_channel.getAsString();
                }
             }

            Response response = new Response();
            response.setResult(result);
            response.setId(id);
            response.setBalance(balance);
            response.setReason(reason);
            response.setDirect_channel(Direct_channel);
            response.setDigital_channel(Digital_channel);

            return response;
        }
    }

