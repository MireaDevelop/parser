package org.Sms.Services.SmSAero;

/**
 * Created by Вадим on 03.07.2016.
 */
public class Response {

    private String result;
    private String reason;
    private String Direct_channel;
    private String Digital_channel;
    private String balance;
    private String id;

    protected Response() {
    }

    public String getResult() {
            return result;
        }

    public void setResult(String result) {
            this.result = result;
        }

    public String getReason() {
            return reason;
        }

    public void setReason(String reason) {
            this.reason = reason;
        }

    public String getDigital_channel() {
            return Digital_channel;
        }

    public void setDigital_channel(String digital_channel) {
            Digital_channel = digital_channel;
        }

    public String getBalance() {
            return balance;
        }

    public void setBalance(String balance) {
            this.balance = balance;
        }

    public String getId() {
            return id;
        }

    public void setId(String id) {
            this.id = id;
        }

    public String getDirect_channel() {
            return Direct_channel;
        }

    public void setDirect_channel(String direct_channel) {
            Direct_channel = direct_channel;
        }
}
