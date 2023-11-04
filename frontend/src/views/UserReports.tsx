import { useContext } from "react";
import { StateContext } from "../utils/state";
import ReportList from "../components/ReportList";

function UserReports() {
  const { global: { user } } = useContext(StateContext);
  if (!user) throw TypeError();

  return (
    <div><ReportList data={[]} /></div>
  );
}

export default UserReports;