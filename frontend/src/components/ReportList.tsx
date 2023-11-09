import { List, Checkbox, Button } from "antd";
import PrijavaListItemField from "./PrijavaListItemField";
import { Prijava } from "../utils/types";
import { useNavigate } from "react-router-dom";

interface ReportListProps {
  data: Prijava[];
  onClick?: (p: Prijava) => void;
}

function ReportList(props: ReportListProps) {
  const navigate = useNavigate();
  return (
    <List bordered style={{ margin: "1em 0", width: "fit-content", height: "fit-content" }}>
      {props.data.map(prijava => (
        <List.Item
          key={prijava.id}
          title={props.onClick ? "Klikni za prikaz lokacije na karti!" : undefined}
          className={props.onClick ? "reportListItemHover" : ""}
          onClick={() => props.onClick && props.onClick(prijava)}
          style={{ padding: "1.5em 2em", display: "flex", textAlign: "center" }}
        >
          <PrijavaListItemField title="ID:" value={prijava.id} />
          <PrijavaListItemField title="Naziv:" value={prijava.naziv} />
          <PrijavaListItemField title="Prijavitelj:" value={prijava.kreator?.username || "Anoniman"} />
          <PrijavaListItemField title="Otklonjeno:" value={<Checkbox className="normalCursor" checked={!!prijava.vrijemeOtklona} />} />
          <PrijavaListItemField title="Naziv:" value={prijava.prvoVrijemePrijave.toLocaleDateString()} />
          <Button style={{ marginLeft: "2em" }} onClick={() => navigate(prijava.id.toString())} type="primary">Detalji</Button>
        </List.Item>
      ))}
    </List>
  );
}

export default ReportList;
