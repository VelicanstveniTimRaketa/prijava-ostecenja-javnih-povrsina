import { useContext } from "react";
import { StateContext } from "../utils/state";
import { useNavigate } from "react-router";
import ReportList from "../components/ReportList";

function UserReports() {
  const { global } = useContext(StateContext);
  const navigate = useNavigate();

  if (!global.user) {
    navigate("/login");
    return <div></div>;
  }

  return (
    <div><ReportList data={[]} /></div>
  );
}

export default UserReports;