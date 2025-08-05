package com.EChowk.EChowk.Service;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
@Slf4j
@Service
public class FirebaseTokenVerifier {
    public FirebaseToken verifyIdToken(String idToken){
        try {
            return FirebaseAuth.getInstance().verifyIdToken(idToken);
        }catch (Exception e){
            log.error("Invalid or Expired Token " ,e);
            throw new RuntimeException("Invalid or Expired Token",e);
        }
    }
}
