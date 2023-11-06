import { Button, Layout } from "antd";
import { Prijava } from "../utils/types";
import MapJsApi from "./MapJsApi";
import { useNavigate } from "react-router-dom";

interface ReportViewProps {
  prijava: Prijava;
}

function ReportView(props: ReportViewProps) {
  const { prijava } = props;
  const navigate = useNavigate();

  return (
    <Layout style={{ margin: "2em" }}>
      <div style={{ display: "flex", justifyContent: "center" }}>
        <div style={{ width: "30em", fontSize: "1.25em" }}>
          <div style={{ fontWeight: "bold" }}>ID:</div>
          <div>{prijava.id}</div>
          <div style={{ fontWeight: "bold" }}>Opis:</div>
          <div>{prijava.opis ? prijava.opis : "<prazan>"}</div>
          <div style={{ fontWeight: "bold" }}>Tip oštećenja:</div>
          <div>{prijava.tipOstecenja.naziv}</div>
          <div style={{ fontWeight: "bold" }}>Parent:</div>
          <div>{prijava.parentPrijava ? prijava.parentPrijava.id : "<nema>"}</div>
          <div style={{ fontWeight: "bold" }}>Slike:</div>
          <div>{prijava.slike.length == 0 ? "<nista>" : prijava.slike[0]}</div>
          <Button style={{ margin: "2em 0" }} onClick={() => navigate(-1)}>Natrag</Button>
        </div>
        <div>
          <MapJsApi />
        </div>
      </div>
    </Layout>
  );
}

export default ReportView;