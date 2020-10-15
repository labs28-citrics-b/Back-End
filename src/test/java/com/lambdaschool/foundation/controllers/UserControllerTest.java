package com.lambdaschool.foundation.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lambdaschool.foundation.models.City;
import com.lambdaschool.foundation.models.User;
import com.lambdaschool.foundation.repository.UserRepository;
import com.lambdaschool.foundation.services.CityService;
import com.lambdaschool.foundation.services.HelperFunctions;
import com.lambdaschool.foundation.services.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

//@RunWith(SpringRunner.class)
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
//    classes = FoundationApplication.class)
//@AutoConfigureMockMvc
//@WithMockUser(username = "admin")
@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private HelperFunctions helperFunctions;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private JwtDecoder jwtDecoder;

    @MockBean
    private UserService userService;

    @MockBean
    private CityService cityService;

    List<User> userList;

    @BeforeEach
    void setUp() {
        userList = new ArrayList<>();

//            City c1 = new City("Washington, District of Columbia");
//            c1.setPopulationdensityrating(20);
//            c1.setSafteyratingscore(90);
//            c1.setCostoflivingscore(15);
//            c1.setAverageincome(110287d);
//            c1.setAveragetemperature(100);
//            c1.setLat(33.3367f);
//            c1.setLon(-90.1234f);
//
//            City c2 = new City("Fort Lauderdale, Florida");
//            c2.setPopulationdensityrating(43);
//            c2.setSafteyratingscore(86);
//            c2.setCostoflivingscore(12);
//            c2.setAverageincome(39477d);
//            c2.setAveragetemperature(70);
//            c2.setLat(32.7673f);
//            c2.setLon(-96.7776f);

        User u1 = new User("Arthur");
        u1.setUserid(1);
        userList.add(u1);
        User u2 = new User("James");
        u2.setUserid(2);
        userList.add(u2);

    }

    @AfterEach
    void tearDown() {
    }

    @Test
    public void listAllUsers() throws Exception {
        String apiUrl = "/users/users";

        Mockito.when(userService.findAll())
            .thenReturn(userList);

        RequestBuilder rb = MockMvcRequestBuilders.get(apiUrl)
            .accept(MediaType.APPLICATION_JSON);

        // the following actually performs a real controller call
        MvcResult r = mockMvc.perform(rb)
            .andReturn(); // this could throw an exception
        String tr = r.getResponse()
            .getContentAsString();

        ObjectMapper mapper = new ObjectMapper();
        String er = mapper.writeValueAsString(userList);

        System.out.println("Expect: " + er);
        System.out.println("Actual: " + tr);

        assertEquals(er,
            tr);
    }

    @Test
    void getUserById() throws
                       Exception {
        String apiUrl = "/users/user/1";

        Mockito.when(userService.findUserById(1))
            .thenReturn(userList.get(1));

        RequestBuilder rb = MockMvcRequestBuilders.get(apiUrl)
            .accept(MediaType.APPLICATION_JSON);
        MvcResult r = mockMvc.perform(rb)
            .andReturn(); // this could throw an exception
        String tr = r.getResponse()
            .getContentAsString();

        ObjectMapper mapper = new ObjectMapper();
        String er = mapper.writeValueAsString(userList.get(1));

        System.out.println("Expect: " + er);
        System.out.println("Actual: " + tr);

        assertEquals(er,
            tr);
    }

    @Test
    void getUserByName() throws
                         Exception {
        String apiUrl = "/users/user/name/Arthur";

        Mockito.when(userService.findByName("Arthur"))
            .thenReturn(userList.get(1));

        RequestBuilder rb = MockMvcRequestBuilders.get(apiUrl)
            .accept(MediaType.APPLICATION_JSON);
        MvcResult r = mockMvc.perform(rb)
            .andReturn(); // this could throw an exception
        String tr = r.getResponse()
            .getContentAsString();

        ObjectMapper mapper = new ObjectMapper();
        String er = mapper.writeValueAsString(userList.get(1));

        System.out.println("Expect: " + er);
        System.out.println("Actual: " + tr);

        assertEquals(er,
            tr);
    }

    @Test
    void getUserLikeName() throws
                           Exception {
        String apiUrl = "/users/user/name/like/jam";

        Mockito.when(userService.findByNameContaining(any(String.class)))
            .thenReturn(userList);

        RequestBuilder rb = MockMvcRequestBuilders.get(apiUrl)
            .accept(MediaType.APPLICATION_JSON);
        MvcResult r = mockMvc.perform(rb)
            .andReturn(); // this could throw an exception
        String tr = r.getResponse()
            .getContentAsString();

        ObjectMapper mapper = new ObjectMapper();
        String er = mapper.writeValueAsString(userList);

        System.out.println("Expect: " + er);
        System.out.println("Actual: " + tr);

        assertEquals(er,
            tr);
    }

    @Test
    void addNewUser() throws
                      Exception {
        String apiUrl = "/users/user";

        Mockito.when(userService.save(any(User.class)))
            .thenReturn(userList.get(0));

        RequestBuilder rb = MockMvcRequestBuilders.post(apiUrl)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .content("{\"username\": \"Priscilla\"}");

        mockMvc.perform(rb)
            .andExpect(status().isCreated())
            .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void updateFullUser() throws
                          Exception {
        String apiUrl = "/users/user/{id}";

        Mockito.when(userService.update(any(User.class),
            any(Long.class)))
            .thenReturn(userList.get(0));

        //        {"userid":2,"username":"james","favcities":[]}

        RequestBuilder rb = MockMvcRequestBuilders.put(apiUrl,
            2L)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .content("{\"username\": \"tigerUpdated\"}");

        mockMvc.perform(rb)
            .andExpect(status().is2xxSuccessful())
            .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void updateUser() throws
                      Exception
    {
        String apiUrl = "/users/user/{id}";

        Mockito.when(userService.update(any(User.class),
            any(Long.class)))
            .thenReturn(userList.get(0));

        //        {"userid":2,"username":"james","favcities":[]}

        RequestBuilder rb = MockMvcRequestBuilders.put(apiUrl,
            2L)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .content("{\"username\": \"tigerUpdated\"}");

        mockMvc.perform(rb)
            .andExpect(status().is2xxSuccessful())
            .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void deleteUserById() throws
                          Exception {
        String apiUrl = "/users/user/{id}";

        RequestBuilder rb = MockMvcRequestBuilders.delete(apiUrl,
            "2")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON);
        mockMvc.perform(rb)
            .andExpect(status().is2xxSuccessful())
            .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void getCurrentUserInfo() throws
                              Exception {
        String apiUrl = "/users/getuserinfo";

        Mockito.when(userService.findByName(anyString()))
            .thenReturn(userList.get(0));

        RequestBuilder rb = MockMvcRequestBuilders.get(apiUrl)
            .accept(MediaType.APPLICATION_JSON);
        MvcResult r = mockMvc.perform(rb)
            .andReturn(); // this could throw an exception
        String tr = r.getResponse()
            .getContentAsString();

        ObjectMapper mapper = new ObjectMapper();
        String er = mapper.writeValueAsString(userList.get(0));

        System.out.println("Expect: " + er);
        System.out.println("Actual: " + tr);

        assertEquals("Rest API Returns List",
            er,
            tr);
    }
}