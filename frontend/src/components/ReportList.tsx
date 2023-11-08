import { List, Checkbox, Button } from "antd";
import PrijavaListItemField from "./PrijavaListItemField";
import { Prijava } from "../utils/types";
import { useNavigate } from "react-router-dom";

interface ReportListProps {
  data: Prijava[];
}

function ReportList(props: ReportListProps) {
  const navigate = useNavigate();
  return (
    <List bordered style={{ margin: "1em 0", width: "fit-content", height: "fit-content" }}>
      {props.data.map(prijava => (
        <List.Item key={prijava.id} style={{ padding: "1.5em 2em", display: "flex", textAlign: "center" }}>
          <PrijavaListItemField title="ID:" value={prijava.id} />
          <PrijavaListItemField title="Tip oštećenja:" value={prijava.tipOstecenja.naziv} />
          <PrijavaListItemField title="Prijavitelj:" value={prijava.kreator?.username || "Anoniman"} />
          <PrijavaListItemField title="Otklonjeno:" value={<Checkbox className="normalCursor" checked={!!prijava.vrijemeOtklona} />} />
          <Button style={{ marginLeft: "2em" }} onClick={() => navigate(prijava.id.toString())} type="primary">Detalji</Button>
        </List.Item>
      ))}
    </List>
  );
}

export default ReportList;
