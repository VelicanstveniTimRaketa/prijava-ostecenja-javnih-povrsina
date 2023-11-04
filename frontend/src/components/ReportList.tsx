import { List, Checkbox } from "antd";
import PrijavaListItemField from "./PrijavaListItemField";
import { Prijava } from "../utils/types";

interface ReportListProps {
  data: Prijava[];
}

function ReportList(props: ReportListProps) {
  return (
    <List bordered style={{ margin: "1em 0", width: "fit-content" }}>
      {props.data.map(prijava => (
        <List.Item key={prijava.id} style={{ padding: "1.5em 2em", display: "flex", textAlign: "center" }}>
          <PrijavaListItemField title="ID:" value={prijava.id} />
          <PrijavaListItemField title="Tip oštećenja:" value={prijava.tipOstecenja.naziv} />
          <PrijavaListItemField title="Prijavitelj:" value={prijava.kreator?.username || "Anoniman"} />
          <PrijavaListItemField title="Povezan:" value={<Checkbox className="normalCursor" checked={!!prijava.parentPrijava} />} />
          <PrijavaListItemField title="Otklonjeno:" value={<Checkbox className="normalCursor" checked={!!prijava.vrijemeOtklona} />} />
        </List.Item>
      ))}
    </List>
  );
}

export default ReportList;
