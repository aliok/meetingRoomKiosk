package tr.com.aliok.meetingroomkiosk.server.service.model;

/**
 * A Java POJO conforming http://developer.android.com/google/gcm/http.html
 * @author Ali Ok (ali.ok@apache.org)
 */
public class GoogleCloudMessageResponse {
    private long multicast_id;
    private int success;
    private int failure;
    private int canonical_ids;
    private GoogleCloudMessageResponseResult[] results;

    public long getMulticast_id() {
        return multicast_id;
    }

    public void setMulticast_id(long multicast_id) {
        this.multicast_id = multicast_id;
    }

    public int getSuccess() {
        return success;
    }

    public void setSuccess(int success) {
        this.success = success;
    }

    public int getFailure() {
        return failure;
    }

    public void setFailure(int failure) {
        this.failure = failure;
    }

    public int getCanonical_ids() {
        return canonical_ids;
    }

    public void setCanonical_ids(int canonical_ids) {
        this.canonical_ids = canonical_ids;
    }

    public GoogleCloudMessageResponseResult[] getResults() {
        return results;
    }

    public void setResults(GoogleCloudMessageResponseResult[] results) {
        this.results = results;
    }
}
