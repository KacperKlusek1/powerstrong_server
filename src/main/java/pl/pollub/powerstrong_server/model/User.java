package pl.pollub.powerstrong_server.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import pl.pollub.powerstrong_server.enums.UserRole;
import pl.pollub.powerstrong_server.enums.UserStatus;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
//Adnotacja @Table jest kluczowa do przeprowadzenia testów integracyjnych
//@Table(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank
    @Column(unique = true)
    private String username;

    @NotBlank
    private String password;

    @Email
    @Column(unique = true)
    private String email;

    @Enumerated(EnumType.STRING)
    @NotNull
    private UserRole role = UserRole.USER;

    @Enumerated(EnumType.STRING)
    @NotNull
    private UserStatus status = UserStatus.ACTIVE;

    private LocalDateTime createDate;

    @OneToMany(mappedBy = "user")
    private Set<UserTrainingPlan> userTrainingPlans;

    @OneToMany(mappedBy = "createdBy")
    private Set<TrainingPlan> createdTrainingPlans;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + role.name()));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return status != UserStatus.BLOCKED;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return status == UserStatus.ACTIVE;
    }
}