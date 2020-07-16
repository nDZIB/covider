package cm.ubuea.covider.userprofile;

import cm.ubuea.covider.registration.domain.MedicalRecord;
import cm.ubuea.covider.registration.domain.Role;
import cm.ubuea.covider.registration.domain.User;
import cm.ubuea.covider.registration.domain.UserLocation;
import cm.ubuea.covider.registration.repository.MedicalRecordRepository;
import cm.ubuea.covider.registration.repository.RoleRepository;
import cm.ubuea.covider.registration.repository.UserLocationRepository;
import cm.ubuea.covider.registration.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

@Component
public class Init implements ApplicationListener<ContextRefreshedEvent> {
    private RoleRepository roleRepository;
    private UserRepository userRepository;
    private MedicalRecordRepository medicalRecordRepository;
    private UserLocationRepository userLocationRepository;

    public Init(RoleRepository roleRepository, UserRepository userRepository,
                MedicalRecordRepository medicalRecordRepository,
                UserLocationRepository userLocationRepository) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.medicalRecordRepository = medicalRecordRepository;
        this.userLocationRepository = userLocationRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {

        Role role = new Role();
        role.setName("USER");
        role = roleRepository.save(role);

        HashSet<Role> roles = new HashSet<>();
        roles.add(role);


        User u = new User();
        u.setId(2L);
        u.setActivated(true);
        u.setEmail("none@mail.ts");
        u.setIdNumber("nocard");
        u.setName("No name user");
        u.setPassword(new BCryptPasswordEncoder().encode("nopass"));
        u.setRoles(roles);

        u = userRepository.save(u);

        MedicalRecord md = new MedicalRecord();
        md.setCurrent_status(true);
        md.setCurrent_symptoms(Arrays.asList("Serious Coughing", "High fevers and chills"));
        md.setUser(u);

        md = medicalRecordRepository.save(md);

        //create a location
        UserLocation location = new UserLocation();
        location.setUser(u);
        location.setCurrent_loctaion("Tiko Douala");
        location.setPrevious_location(Arrays.asList("Buea", "Tiko", "Limbe", "Ghana"));

        userLocationRepository.save(location);

    }
}
