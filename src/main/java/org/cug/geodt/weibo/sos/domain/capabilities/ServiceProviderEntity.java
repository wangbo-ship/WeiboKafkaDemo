package org.cug.geodt.weibo.sos.domain.capabilities;


/**
 * Author WJW
 * Date 2023/6/7 9:55
 */

public class ServiceProviderEntity {
    private String ProviderName;
    private String ProviderSite;
    private ServiceContactEntity serviceContact;

    public ServiceProviderEntity(String providerName, String providerSite, ServiceContactEntity serviceContact) {
        ProviderName = providerName;
        ProviderSite = providerSite;
        this.serviceContact = serviceContact;
    }

    public ServiceProviderEntity() {
    }

    public String getProviderName() {
        return ProviderName;
    }

    public void setProviderName(String providerName) {
        ProviderName = providerName;
    }

    public String getProviderSite() {
        return ProviderSite;
    }

    public void setProviderSite(String providerSite) {
        ProviderSite = providerSite;
    }

    public ServiceContactEntity getServiceContact() {
        return serviceContact;
    }

    public void setServiceContact(ServiceContactEntity serviceContact) {
        this.serviceContact = serviceContact;
    }
}
