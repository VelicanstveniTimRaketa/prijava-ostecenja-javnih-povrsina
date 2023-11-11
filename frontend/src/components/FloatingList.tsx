import { Layout, List, Typography } from "antd";

interface FloatingListProps {
  data: {
    text: string;
    icon?: React.ReactNode;
    onClick?: () => void;
  }[];
}

function FloatingList(props: FloatingListProps) {
  return (
    <Layout
      className="shadow"
      style={{
        position: "absolute",
        zIndex: 1,
        right: 0,
        top: "100%",
        width: "15em",
        marginTop: "1em",
      }}
    >
      <List style={{ textAlign: "right" }}>
        {props.data.map(item => (
          <List.Item
            key={item.text}
            onClick={item.onClick}
            className="reportListItemHover"
            style={{ justifyContent: "center", gap: "1em", cursor: "pointer" }}
          >
            {item.icon}
            <Typography>{item.text}</Typography>
          </List.Item>
        ))}
      </List>
    </Layout>
  );
}

export default FloatingList;