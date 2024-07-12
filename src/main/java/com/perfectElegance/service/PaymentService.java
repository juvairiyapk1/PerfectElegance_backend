//package com.perfectElegance.service;
//
//import com.perfectElegance.Dto.PaymentRequest;
//import com.perfectElegance.Dto.PaymentResponse;
//import com.perfectElegance.modal.Payment;
//import com.perfectElegance.modal.User;
//import com.perfectElegance.repository.PaymentRepository;
//import com.perfectElegance.repository.UserRepository;
//import com.stripe.exception.StripeException;
//import com.stripe.model.PaymentIntent;
//import com.stripe.param.PaymentIntentCreateParams;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//import java.util.Optional;
//
//@Service
//public class PaymentService {
//
//    @Autowired
//    private PaymentRepository paymentRepository;
//
//    @Autowired
//    private UserRepository userRepository;
//    public PaymentResponse createPaymentIntent(PaymentRequest request, Integer userId) throws StripeException {
//
//        User user = userRepository.findById(userId)
//                .orElseThrow(() -> new UsernameNotFoundException("User not found with id " + userId));
//
//        Optional<Payment> existingPayment = paymentRepository.findByUserId(userId);
//        if (existingPayment.isPresent()) {
//            Payment payment = existingPayment.get();
//            return new PaymentResponse(
//                    payment.getPaymentId(),
//                    "Payment already exists",
//                    payment.getPaymentId());
//        }
//
//        PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
//                .setAmount(request.getAmount())
//                .setCurrency("usd")
//                .build();
//
//        PaymentIntent paymentIntent = PaymentIntent.create(params);
//
//        Payment payment = new Payment();
//        payment.setPaymentId(paymentIntent.getId());
//        payment.setAmount(request.getAmount());
//        payment.setCurrency("usd");
//        payment.setPaymentMethod(paymentIntent.getPaymentMethod());
//        payment.setUser(user);
//        // Set other fields as needed
//        paymentRepository.save(payment);
//
//        return new PaymentResponse( paymentIntent.getId(),"payment success",paymentIntent.getClientSecret());
//    }
//
//}
