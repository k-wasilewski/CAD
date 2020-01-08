import javax.swing.filechooser.FileFilter;
import java.io.File;

//*.cvs extension
@SuppressWarnings("SwitchStatementWithTooFewBranches")
public class CvsExt extends FileFilter
{
    @SuppressWarnings("CanBeFinal")
    private String type;

    public CvsExt(String type)
    {
        this.type = type;
    }

    public boolean accept(File file)
    {
        if(file.isDirectory())
            return true;

        String ext = getExtension(file);
        if(ext == null)
            return false;

        switch(type)
        {
            case "cvs":
                if(ext.equals("cvs"))
                    return true;
                else
                    break;
            default:
                System.out.println(type + " is not supported");
        }

        return false;
    }

    public String getDescription()
    {
        switch(type)
        {
            case "battle":
                return "Only *.cvs file supported";
        }
        return null;
    }

    public String getExtension(File f)
    {
        String ext = null;
        String filename = f.getName();

        int i = filename.lastIndexOf('.');

        if(i > 0 && i < filename.length() - 1)
            ext = filename.substring(i + 1).toLowerCase();

        return ext;
    }
}