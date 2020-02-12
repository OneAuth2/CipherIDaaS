package cipher.console.oidc.domain.zhonghe;

import java.io.Serializable;
import java.util.List;

public class ZhongheUser implements Serializable {


    /**
     * posts : [217]
     * staff_name : 张立松
     * department : 278
     * pk : 5
     * post : 217
     * staff_status : official
     * user_name : zhanglisong
     * email : zhanglisong@unittec.com
     * departments : [278]
     */

    private String staff_name;
    private int department;
    private int pk;
    private int post;
    private String staff_status;
    private String user_name;
    private String email;
    private List<Integer> posts;
    private List<Integer> departments;

    public String getStaff_name() {
        return staff_name;
    }

    public void setStaff_name(String staff_name) {
        this.staff_name = staff_name;
    }

    public int getDepartment() {
        return department;
    }

    public void setDepartment(int department) {
        this.department = department;
    }

    public int getPk() {
        return pk;
    }

    public void setPk(int pk) {
        this.pk = pk;
    }

    public int getPost() {
        return post;
    }

    public void setPost(int post) {
        this.post = post;
    }

    public String getStaff_status() {
        return staff_status;
    }

    public void setStaff_status(String staff_status) {
        this.staff_status = staff_status;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPosts() {
        StringBuffer stringBuffer = new StringBuffer();

        for (int i = 0; i < posts.size(); i++) {
            if (i == posts.size() - 1) {
                stringBuffer.append(posts.get(i));
            } else {
                stringBuffer.append(posts.get(i)).append(",");
            }
        }
        return stringBuffer.toString();
    }

    public void setPosts(List<Integer> posts) {
        this.posts = posts;
    }

    public String getDepartments() {

        StringBuffer stringBuffer = new StringBuffer();

        for (int i = 0; i < departments.size(); i++) {
            if (i == departments.size() - 1) {
                stringBuffer.append(departments.get(i));
            } else {
                stringBuffer.append(departments.get(i)).append(",");
            }
        }
        return stringBuffer.toString();

    }

    public void setDepartments(List<Integer> departments) {
        this.departments = departments;
    }


    @Override
    public String toString() {
        return "ZhongheUser{" +
                "staff_name='" + staff_name + '\'' +
                ", department=" + department +
                ", pk=" + pk +
                ", post=" + post +
                ", staff_status='" + staff_status + '\'' +
                ", user_name='" + user_name + '\'' +
                ", email='" + email + '\'' +
                ", posts=" + posts +
                ", departments=" + departments +
                '}';
    }
}
