package com.perfectElegance.service;

import com.perfectElegance.exceptions.EmailAlreadyExistsException;
import com.perfectElegance.modal.Partner;
import com.perfectElegance.modal.Profile;
import com.perfectElegance.modal.User;
import com.perfectElegance.repository.PartnerRepository;
import com.perfectElegance.repository.ProfileRepository;
import com.perfectElegance.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private static final Logger logger = LoggerFactory.getLogger(ProfileService.class);


    private final ProfileRepository profileRepository;

    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final PartnerRepository partnerRepository;


    public Profile saveProfile(Profile profile){
        return profileRepository.save(profile);
    }

    public User getUserByUserId(Integer userId) {
        System.out.println("Finding profile for user ID: " + userId);
        User user = userRepository.findById(userId).get();
        System.out.println("Found profile: " + user);
        return user;
    }



    public Profile createProfile(Profile profile, User user){

        Optional<User> existingUser = userRepository.findByEmail(user.getEmail());
        if (existingUser.isPresent()) {
            throw new EmailAlreadyExistsException("Email already exists");
        }
        Profile createdProfile = modelMapper.map(profile,Profile.class);
        return profileRepository.save(profile);

    }


    public Profile updateProfile(Integer userId, Profile profileDet){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with id " + userId));

        Profile existingProfile=user.getProfile();
        System.out.println(existingProfile+"existingProfile");
        if (existingProfile == null) {
            Profile newProfile=new Profile();
            newProfile.setUser(user);
            profileDet.setUser(user);
            return profileRepository.save(profileDet);
        }else{
            System.out.println("hi");
            Integer existingProfileId = existingProfile.getId();
            System.out.println(existingProfileId+"existingProfileId");
            existingProfile.setWillingToRelocate(profileDet.isWillingToRelocate());
            existingProfile.setMarriagePlans(profileDet.getMarriagePlans());
            existingProfile.setEducationInstitute(profileDet.getEducationInstitute());
            existingProfile.setLanguagesKnown(profileDet.getLanguagesKnown());
            existingProfile.setId(existingProfileId);
            return profileRepository.save(existingProfile);
        }

    }

    public User findById(Integer userId){
        return userRepository.findById(userId).orElse(null);
    }

    public Profile findByUserId(Integer userId) {
        return profileRepository.findByUserId(userId);
    }


    public Profile updatePhysicalAttributes(Integer userId, Profile physicalData) {
        User user=userRepository.findById(userId)
                .orElseThrow(()->new UsernameNotFoundException("User not found with id " + userId));

        Profile existingProfile=user.getProfile();

        if(existingProfile == null){
            Profile newProfile = new Profile();
            newProfile.setUser(user);
            physicalData.setUser(user);
            return profileRepository.save(physicalData);
        }else {
            Integer existingProfileId = existingProfile.getId();
            existingProfile.setBloodGroup(physicalData.getBloodGroup());
            existingProfile.setHairColor(physicalData.getHairColor());
            existingProfile.setHairType(physicalData.getHairType());
            existingProfile.setEyeColor(physicalData.getEyeColor());
            existingProfile.setId(existingProfileId);
            return profileRepository.save(existingProfile);
        }
    }

    public Profile uapdataFamily(Integer userId, Profile familyData) {
        User user=userRepository.findById(userId)
                .orElseThrow(()->new UsernameNotFoundException("User not found with id " + userId));
        Profile existingProfile=user.getProfile();

        if(existingProfile == null){
            Profile newProfile = new Profile();
            newProfile.setUser(user);
            familyData.setUser(user);
            return profileRepository.save(familyData);
        }else {
            Integer existingProfileId = existingProfile.getId();
            existingProfile.setFamilyType(familyData.getFamilyType());
            existingProfile.setHomeType(familyData.getHomeType());
            existingProfile.setLivingSituation(familyData.getLivingSituation());
            existingProfile.setId(existingProfileId);
            return profileRepository.save(existingProfile);
        }

    }


    public Partner updatePartnerPref(Integer userId, Partner partnerData) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with id " + userId));
        Partner existingPartner = user.getPartner();
        Profile existinProfile = user.getProfile();

        if(existingPartner == null){
            Partner newPartner = new Partner();
            newPartner.setUser(user);
            partnerData.setUser(user);

            return partnerRepository.save(partnerData);
        }else {
            Integer existingPartnerId = existingPartner.getId();
            existingPartner.setAge(partnerData.getAge());
            existingPartner.setMaritalStatus(partnerData.getMaritalStatus());
            existingPartner.setHeight(partnerData.getHeight());
            existingPartner.setComplexion(partnerData.getComplexion());
            existingPartner.setPhysicalStatus(partnerData.getPhysicalStatus());
            existingPartner.setLanguagesSpoken(partnerData.getLanguagesSpoken());
            existingPartner.setDrinkingHabits(partnerData.isDrinkingHabits());
            existingPartner.setReligion(partnerData.getReligion());
            existingPartner.setAppearance(partnerData.getAppearance());
            existingPartner.setMotherTongue(partnerData.getMotherTongue());
            existingPartner.setId(existingPartnerId);
            return partnerRepository.save(existingPartner);
        }

    }


    public Partner updatePartnerEducation(Integer userId,Partner partnerData) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with id " + userId));
        Partner existingPartner = user.getPartner();

        if (existingPartner == null) {
            Partner newPartner = new Partner();
            newPartner.setUser(user);
            partnerData.setUser(user);

            return partnerRepository.save(partnerData);
        } else {
            existingPartner.setEducation(partnerData.getEducation());
            existingPartner.setProfession(partnerData.getProfession());
            existingPartner.setCountry(partnerData.getCountry());
            existingPartner.setCity(partnerData.getCity());
            return partnerRepository.save(existingPartner);
        }

    }


}
