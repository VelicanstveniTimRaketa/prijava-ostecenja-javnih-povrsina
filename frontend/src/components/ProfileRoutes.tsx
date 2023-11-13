import { useContext, useEffect } from "react";
import { Route, Routes } from "react-router";
import { useNavigate } from "react-router";
import { StateContext } from "../utils/state";
import Profile from "../views/Profile";
import UserReports from "../views/UserReports";
import EditProfile from "../views/EditProfile";
import Report from "../pages/Report";

function ProfileRoutes () {
  const { global } = useContext(StateContext);
  const navigate = useNavigate();

  useEffect(() => {
    if (!global.user) navigate("/login");
  }, [global.user, navigate]);

  if (!global.user) return <div></div>;

  return (
    <Routes>
      <Route path="" element={<Profile />} />
      <Route path="reports" element={<UserReports />} />
      <Route path="reports/:id" element={<Report enableEditing={true} />} />
      <Route path="edit" element={<EditProfile />} />
    </Routes>
  );
}

export default ProfileRoutes;