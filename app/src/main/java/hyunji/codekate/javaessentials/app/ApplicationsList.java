package hyunji.codekate.javaessentials.app;

import java.util.List;

import hyunji.codekate.javaessentials.vo.AppInfo;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Accessors(prefix = "m")
public class ApplicationsList {

    private static ApplicationsList ourInstance = new ApplicationsList();

    @Getter
    @Setter
    private List<AppInfo> mList;

    private ApplicationsList() {
    }

    public static ApplicationsList getInstance() {
        return ourInstance;
    }
}
