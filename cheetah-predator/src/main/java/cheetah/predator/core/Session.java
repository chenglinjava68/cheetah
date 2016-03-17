package cheetah.predator.core;

import cheetah.predator.protocol.Message;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Created by Max on 2016/3/13.
 */
public interface Session {
    enum State {
        UNAPPROVED, APPROVED
    }

    class Metadata {
        private String tenantId;
        private String clientId;
        private String clientSecret;
        private String deviceId;
        private String devicePlatform;

        private int compressType;
        private int digestType;
        private int encryptType;

        private String remoteEndpoint;
        private String accessEndpoint;
        private Session.State state;

        Metadata(MetadataBuilder builder) {
            this.tenantId = builder.tenantId;
            this.clientId = builder.clientId;
            this.clientSecret = builder.clientSecret;
            this.deviceId = builder.deviceId;
            this.devicePlatform = builder.devicePlatform;
            this.compressType = builder.compressType;
            this.digestType = builder.digestType;
            this.encryptType = builder.encryptType;
            this.remoteEndpoint = builder.remoteEndpoint;
            this.accessEndpoint = builder.accessEndpoint;
            this.state = builder.state;
        }

        public String tenantId() {
            return tenantId;
        }

        public String clientId() {
            return clientId;
        }

        public String clientSecret() {
            return clientSecret;
        }

        public String deviceId() {
            return deviceId;
        }

        public String devicePlatform() {
            return devicePlatform;
        }

        public int compressType() {
            return compressType;
        }

        public int digestType() {
            return digestType;
        }

        public int encryptType() {
            return encryptType;
        }

        public String remoteEndpoint() {
            return remoteEndpoint;
        }

        public String accessEndpoint() {
            return accessEndpoint;
        }

        public Session.State state() {
            return state;
        }

        @Override
        public String toString() {
            return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
        }

        /**
         * @return
         */
        public MetadataBuilder toBuilder() {
            return newBuilder()
                    .tenantId(tenantId)
                    .clientId(clientId)
                    .clientSecret(clientSecret)
                    .deviceId(deviceId)
                    .devicePlatform(devicePlatform)
                    .remoteEndpoint(remoteEndpoint)
                    .accessEndpoint(accessEndpoint)
                    .compressType(compressType)
                    .digestType(digestType)
                    .encryptType(encryptType)
                    .state(state);
        }

        /**
         * @return
         */
        public static MetadataBuilder newBuilder() {
            return new MetadataBuilder();
        }
    }

    class MetadataBuilder {
        String tenantId;
        String clientId;
        String clientSecret;
        String deviceId;
        String devicePlatform;

        int compressType;
        int digestType;
        int encryptType;

        String remoteEndpoint;
        String accessEndpoint;
        Session.State state;

        MetadataBuilder() {
        }

        public MetadataBuilder tenantId(String tenantId) {
            this.tenantId = tenantId;
            return this;
        }

        public MetadataBuilder clientId(String clientId) {
            this.clientId = clientId;
            return this;
        }

        public MetadataBuilder clientSecret(String clientSecret) {
            this.clientSecret = clientSecret;
            return this;
        }

        public MetadataBuilder deviceId(String deviceId) {
            this.deviceId = deviceId;
            return this;
        }

        public MetadataBuilder devicePlatform(String devicePlatform) {
            this.devicePlatform = devicePlatform;
            return this;
        }

        public MetadataBuilder compressType(int compressType) {
            this.compressType = compressType;
            return this;
        }

        public MetadataBuilder digestType(int digestType) {
            this.digestType = digestType;
            return this;
        }

        public MetadataBuilder encryptType(int encryptType) {
            this.encryptType = encryptType;
            return this;
        }

        public MetadataBuilder remoteEndpoint(String remoteEndpoint) {
            this.remoteEndpoint = remoteEndpoint;
            return this;
        }

        public MetadataBuilder accessEndpoint(String accessEndpoint) {
            this.accessEndpoint = accessEndpoint;
            return this;
        }

        public MetadataBuilder state(Session.State state) {
            this.state = state;
            return this;
        }

        public Metadata build() {
            return new Metadata(this);
        }
    }

    /**
     * @return
     */
    Session.Metadata metadata();

    /**
     * @param metadata
     */
    void metadata(Session.Metadata metadata);

    void respond(Message message);

    void close(Message message) throws Exception;

}
