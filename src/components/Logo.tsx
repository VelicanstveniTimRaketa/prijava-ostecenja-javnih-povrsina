import { Image } from "antd";
import LogoImage from "../assets/logo.svg";
import { Link } from "react-router-dom";

function Logo() {
  return (
    <Link to="/">
      <Image src={LogoImage} preview={false} alt="Logo Image" />
    </Link>
  );
}

export default Logo;
