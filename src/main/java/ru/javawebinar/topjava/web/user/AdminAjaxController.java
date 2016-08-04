package ru.javawebinar.topjava.web.user;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;

import java.util.List;

/**
 * User: grigory.kislin
 */
@RestController
@RequestMapping("/ajax/admin/users")
public class AdminAjaxController extends AbstractUserController {

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<User> getAll() {
        return super.getAll();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") int id) {
        super.delete(id);
    }

    @RequestMapping(method = RequestMethod.POST)
    public void createOrUpdate(@RequestParam("id") int id,
                               @RequestParam("name") String name,
                               @RequestParam("email") String email,
                               @RequestParam("password") String password,
                               @RequestParam(value = "enabled", required = false) String enabled) {


        User user = new User(id, name, email, password, Role.ROLE_USER);
        if (enabled != null)
            user.setEnabled(Boolean.valueOf(enabled));
        if (id == 0) {
            super.create(user);
        } else {
            super.update(user, id);
        }
    }
}
