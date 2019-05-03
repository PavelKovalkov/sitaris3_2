package course.project.integration

import course.project.service.impl.DefaultPasswordEncoder
import spock.lang.Specification
import spock.lang.Subject

class DefaultPasswordEncoderTest extends Specification {
    @Subject
    def passwordEncoder = new DefaultPasswordEncoder()

    def "generates every time the same hash code for the same password"() {
        given:
            def password = 'password'
            Set encodedPassword = []

        when:
            1.upto(4) {
                encodedPassword.add(passwordEncoder.encodePassword(password))
            }

        then: 'set contains only one element'
            encodedPassword.size() == 1
    }
}
