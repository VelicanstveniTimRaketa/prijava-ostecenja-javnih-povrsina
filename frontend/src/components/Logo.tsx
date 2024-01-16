import { Image } from "antd";
import { Link } from "react-router-dom";
import LogoImage from "../assets/logo.webp";

interface LogoProps {
  size?: string;
}

function Logo(props: LogoProps) {
  return (
    <Link to="/">
      <Image src={LogoImage} preview={false} alt="Logo Image" style={{ height: props.size ?? "2.5em" }} />
    </Link>
  );
}

export default Logo;
