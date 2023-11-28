import { List } from "antd";

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
          onClick={entry.onClick}
          style={{ padding: "1.5em 2em", display: "flex", textAlign: "center" }}
        >
          {entry.items.map((item, i) => (
            <div key={item.title || i} style={{ minWidth: "5em", maxWidth: "30em", margin: "0.5em" }}>
              {item.title && <div style={{ fontWeight: "bold" }}>{item.title}</div>}
              <div>{item.value}</div>
            </div>
          ))}
        </List.Item>
      ))}
    </List>
  );
}

export default CustomList;
