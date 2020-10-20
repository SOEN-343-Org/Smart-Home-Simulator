package test;

import org.junit.jupiter.api.*;
import org.soen343.models.Window;

public class U7Test {

    public static Window window;

    @BeforeEach
    public void setup(){
        int id= 3; // arbitrary id
        window= new Window(id);
    }

    @AfterEach
    public void teardown(){
        window=null;
    }

    @Test
    public void checkBlockedWindow_whenWindowIsNotBlocked_returnsFalse(){
        window.setBlocked(false);
        boolean isBlocked = window.isBlocked();
        Assertions.assertFalse(isBlocked);
    }

    @Test
    public void checkBlockedWindow_whenWindowIsBlocked_returnsTrue(){
        window.setBlocked(true);
        boolean isBlocked= window.isBlocked();
        Assertions.assertTrue(isBlocked);
    }

    @Test
    public void checkIfWindowIsOpen_whenWindowIsOpen_returnsTrue(){
        window.setOpen(true);
        boolean isOpen= window.isOpen();
        Assertions.assertTrue(isOpen);
    }

    @Test
    public void checkIfWindowIsOpen_whenWindowIsClosed_returnsFalse(){
        window.setOpen(false);
        boolean isOpen= window.isOpen();
        Assertions.assertFalse(isOpen);
    }


}
