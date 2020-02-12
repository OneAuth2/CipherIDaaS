import authLogic from "@/store/authLogic/mutations";
import loginedShow from "@/store/loginedShow/mutations";
export default {
  ...authLogic,
  ...loginedShow
};
