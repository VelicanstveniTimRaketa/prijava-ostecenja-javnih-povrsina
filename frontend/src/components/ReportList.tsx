import { Checkbox, Button } from "antd";
import { Prijava } from "../utils/types";
import { useNavigate } from "react-router-dom";
import CustomList from "./CustomList";

interface ReportListProps {
  data: Prijava[];
  onClick?: (p: Prijava) => void;
}

function ReportList(props: ReportListProps) {
  const navigate = useNavigate();

  const items = props.data.map(prijava => ({
    id: prijava.id,
    onClick: () => props.onClick && props.onClick(prijava),
    items: [
      { title: "ID:", value: prijava.id },
      { title: "Naziv:", value: prijava.naziv },
      { title: "Prijavitelj:", value: prijava.kreator?.username || "Anoniman" },
      { title: "Datum prijave:", value: prijava.prvoVrijemePrijave.toLocaleDateString() },
      { title: "Otklonjeno:", value: <Checkbox className="normalCursor" checked={!!prijava.vrijemeOtklona} /> },
      { value: <Button style={{ marginLeft: "2em" }} onClick={() => navigate(prijava.id.toString(), { state: { prijava: prijava } })} type="primary">Detalji</Button>}
    ]
  }));
  return (
    <CustomList onHoverText="Klikni za prikaz lokacije na karti!" data={items} />
  );
}

export default ReportList;
