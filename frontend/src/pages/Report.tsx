import { useNavigate, useParams } from "react-router-dom";
import { useEffect, useState } from "react";
import { getPrijava } from "../utils/fetch";
import { Prijava } from "../utils/types";
import ReportView from "../components/ReportView";

function Report() {
  const [prijava, setPrijava] = useState<Prijava>();
  const { id } = useParams(); 
  const navigate = useNavigate();
  const realId = Number.parseInt(id as string);

  const quit = isNaN(realId) || id === undefined;

  useEffect(() => {
   if (quit) navigate("/");
  }, [quit, navigate]);

  useEffect(() => {
    if (quit) return;
    getPrijava(realId).then(val => setPrijava(val.data));
  }, [quit, realId]);

  if (quit) return <div></div>;
  if (!prijava) return <div>Loading</div>;

  return (
    <ReportView prijava={prijava} />
  );
}

export default Report;