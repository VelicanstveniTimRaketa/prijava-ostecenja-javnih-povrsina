import { useEffect, useState } from "react";
import { useLocation, useNavigate, useParams } from "react-router-dom";
import { getPrijava } from "../utils/fetch";
import { Prijava } from "../utils/types";
import { Button, Layout } from "antd";
import { locationToGoogle } from "../utils/location";
import MapJsApi from "../components/MapJsApi";
import Check from "../components/Check";

interface ReportProps {
  enableEditing?: boolean
}

function Report(props: ReportProps) {
  const location = useLocation();
  const [prijava, setPrijava] = useState<Prijava | undefined>(location.state.prijava);
  const { id } = useParams();
  const navigate = useNavigate();
  const realId = Number.parseInt(id as string);

  const isIdBad = isNaN(realId) || id === undefined;

  useEffect(() => {
    if (isIdBad || prijava) return;
    getPrijava(realId).then(val => setPrijava(val.data));
  }, [isIdBad, prijava, realId]);


  if (isIdBad || !prijava) return <div>Loading</div>;
  const marker = locationToGoogle(prijava.lokacija);
  console.log(prijava);

  return (
    <Check if={!isIdBad && !!prijava} elseNavigateTo="/">
      <Layout style={{ margin: "2em" }}>
        <div style={{ display: "flex", justifyContent: "center" }}>
          <div style={{ width: "30em", fontSize: "1.25em" }}>
            <div style={{ fontWeight: "bold" }}>ID:</div>
            <div>{prijava.id}</div>
            <div style={{ fontWeight: "bold" }}>Opis:</div>
            <div>{prijava.opis ? prijava.opis : "<prazan>"}</div>
            <div style={{ fontWeight: "bold" }}>Tip oštećenja:</div>
            <div>{prijava.tipOstecenja?.naziv}</div>
            <div style={{ fontWeight: "bold" }}>Parent:</div>
            <div>{prijava.parentPrijava ? prijava.parentPrijava.id : "<nema>"}</div>
            <div style={{ fontWeight: "bold" }}>Slike:</div>
            <div>{prijava.slike.length == 0 ? "<nista>" : prijava.slike[0].podatak}</div>
            <Button style={{ margin: "2em 2em 0 0" }} onClick={() => navigate(-1)}>Natrag</Button>
            {props.enableEditing && <Button danger>Izbriši prijavu</Button>}
          </div>
          <div>
            <MapJsApi
              zoom={16}
              center={marker}
              marker={marker}
            />
          </div>
        </div>
      </Layout>
    </Check>
  );
}

export default Report;