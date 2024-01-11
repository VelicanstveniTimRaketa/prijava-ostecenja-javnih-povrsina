import { WarningOutlined, CloseCircleOutlined } from "@ant-design/icons";
import { Card } from "antd";

interface AlertBannerProps {
  type?: "warning" | "error",
  message: string;
}

function AlertBanner(props: AlertBannerProps) {

  return (
    <Card bordered={false}>
      <div style={{ display: "flex", gap: "1em", alignItems: "center", fontSize: "1.5em" }}>
        {props.type == "warning" ? <WarningOutlined /> : <CloseCircleOutlined /> }
        <div>
          {props.message}
        </div>
      </div>
    </Card>
  );
}

export default AlertBanner;
