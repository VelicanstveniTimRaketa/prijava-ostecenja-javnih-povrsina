import { List } from "antd";
import PrijavaListItemField from "./PrijavaListItemField";

interface Entry {
  id: number;
  items: {
    title?: string;
    value: number | string | React.ReactElement;
  }[];
  onClick?: () => void;
}

interface CustomListProps {
  data: Entry[]
  onClick?: () => void;
  onHoverText?: string
}

function CustomList(props: CustomListProps) {
  return (
    <List bordered style={{ margin: "1em 0", width: "fit-content", height: "fit-content" }}>
      {props.data.map(entry => (
        <List.Item
          key={entry.id}
          title={props.onHoverText}
          className={entry.onClick ? "reportListItemHover" : ""}
          onClick={() => entry.onClick && entry.onClick()}
          style={{ padding: "1.5em 2em", display: "flex", textAlign: "center" }}
        >
          {entry.items.map((item, i) => (
            <PrijavaListItemField key={item.title || i} title={item.title} value={item.value} />
          ))}
        </List.Item>
      ))}
    </List>
  );
}

export default CustomList;
