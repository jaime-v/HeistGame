package groupOne.game;
import org.junit.Test;

public class UITest {
    GamePanel gp = new GamePanel();
    UI ui = new UI(gp);
    
    @Test
    public void addMessageTest(){
        ui.addMessage("TestString");

        assert(ui.message != null);
        assert(ui.message.get(0).toString() != "");
        ui.resetUI();
        assert(ui.message.isEmpty());
    }
}
