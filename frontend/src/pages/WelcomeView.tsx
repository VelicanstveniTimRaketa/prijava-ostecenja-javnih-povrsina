import { Layout, Typography, theme } from "antd";
import { Content } from "antd/es/layout/layout";
import { useNavigate } from "react-router-dom";
import Title from "antd/es/typography/Title";
import BigButton from "../components/BigButton";
import MapJsApi from "../components/MapJsApi";

function WelcomeView() {
  const { token: { colorBgContainer } } = theme.useToken();
  const navigate = useNavigate();

  return (
    <Layout
      style={{ padding: "2em 4em", display: "flex", flexDirection: "row" }}>
      <Content style={{ margin: 0, minHeight: 280 }}>
        <Title level={1}>Prijavi oštećenje na cesti</Title>
        <Typography.Paragraph style={{ fontSize: "1.5em" }}>
          Odmah i sada!
        </Typography.Paragraph>
        <Layout style={{ display: "flex", flexDirection: "row", gap: "1em" }}>
          <BigButton onClick={() => navigate("search")} bold>Pretraži</BigButton>
          <BigButton onClick={() => navigate("report")} bold>Prijavi</BigButton>
        </Layout>
      </Content>
      <Layout style={{ flex: 1, height: "fit-content", background: colorBgContainer }}>
        <MapJsApi />
      </Layout>
    </Layout>
  );
}

export default WelcomeView;
