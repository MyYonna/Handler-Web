package org.handler.property.entity.service.impl;

import org.handler.property.entity.service.UploadService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.qiniu.util.Auth;

@Service
public class UploadServiceImpl implements UploadService {

	@Value("${qiniu_accessKey}")
	private String qiniuAccessKey;
	@Value("${qiniu_secretKey}")
	private String qiniuSecretKey;
	//七牛图片上传空间
    @Value("${qiniu_sys_pic_space}")
	private String uploadSpace;
	//七牛图片上传空间
    @Value("${qiniu_product_pic_url}")
	private String domainname;
	@Override
	public String getToken() {
		// TODO Auto-generated method stub
		 Auth auth = Auth.create(qiniuAccessKey, qiniuSecretKey);
        String token = auth.uploadToken(uploadSpace);
        return token;
	}

	@Override
	public String getDomainName() {
		// TODO Auto-generated method stub
		return domainname;
	}

}
