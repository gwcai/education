package com.genius.coder.education.properties;

/**
 * @author GaoWeicai.(lili14520 @ gmail.com)
 * @date 2020/3/5
 */
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(
        prefix = "token.redis"
)
@ConditionalOnProperty(
        prefix = "token.redis",
        value = {"host"}
)
public class TokenRedisProperties {
    private int database = 0;
    private String host;
    private int port = 6379;
    private String password;

    public TokenRedisProperties() {
    }

    public int getDatabase() {
        return this.database;
    }

    public String getHost() {
        return this.host;
    }

    public int getPort() {
        return this.port;
    }

    public String getPassword() {
        return this.password;
    }

    public void setDatabase(final int database) {
        this.database = database;
    }

    public void setHost(final String host) {
        this.host = host;
    }

    public void setPort(final int port) {
        this.port = port;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof TokenRedisProperties)) {
            return false;
        } else {
            TokenRedisProperties other = (TokenRedisProperties)o;
            if (!other.canEqual(this)) {
                return false;
            } else if (this.getDatabase() != other.getDatabase()) {
                return false;
            } else {
                label41: {
                    Object this$host = this.getHost();
                    Object other$host = other.getHost();
                    if (this$host == null) {
                        if (other$host == null) {
                            break label41;
                        }
                    } else if (this$host.equals(other$host)) {
                        break label41;
                    }

                    return false;
                }

                if (this.getPort() != other.getPort()) {
                    return false;
                } else {
                    Object this$password = this.getPassword();
                    Object other$password = other.getPassword();
                    if (this$password == null) {
                        if (other$password != null) {
                            return false;
                        }
                    } else if (!this$password.equals(other$password)) {
                        return false;
                    }

                    return true;
                }
            }
        }
    }

    protected boolean canEqual(final Object other) {
        return other instanceof TokenRedisProperties;
    }

    public int hashCode() {
        int result = 1;
        result = result * 59 + this.getDatabase();
        Object $host = this.getHost();
        result = result * 59 + ($host == null ? 43 : $host.hashCode());
        result = result * 59 + this.getPort();
        Object $password = this.getPassword();
        result = result * 59 + ($password == null ? 43 : $password.hashCode());
        return result;
    }

    public String toString() {
        return "TokenRedisProperties(database=" + this.getDatabase() + ", host=" + this.getHost() + ", port=" + this.getPort() + ", password=" + this.getPassword() + ")";
    }
}
