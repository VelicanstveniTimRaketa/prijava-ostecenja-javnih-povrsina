import { Route, Routes } from "react-router";
import Profile from "../views/Profile";
import UserReports from "../views/UserReports";
import { useNavigate } from "react-router";
import { useContext } from "react";
import { StateContext } from "../utils/state";
import EditProfile from "../views/EditProfile";

function ProfileRoutes () {
  const { global } = useContext(StateContext);
  const navigate = useNavigate();

  if (!global.user) {
    navigate("/login");
    return <div></div>;
  }

  return (
    <Routes>
      <Route path="" element={<Profile />} />
      <Route path="reports" element={<UserReports />} />
      <Route path="edit" element={<EditProfile />} />
    </Routes>
  );
}

export default ProfileRoutes;