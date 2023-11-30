import { Autocomplete } from "@react-google-maps/api";
import { Input } from "antd";
import { useState } from "react";

interface PlacesInputProps {
  onClick: (point: google.maps.LatLngLiteral) => void;
}

function PlacesInput(props: PlacesInputProps) {
  const [autoComplete, setAutocomplete] = useState<google.maps.places.Autocomplete>();

  return (
    <Autocomplete
      onLoad={setAutocomplete}
      options={{
        componentRestrictions: { country: "hr" },
      }}
      onPlaceChanged={() => {
        const point = autoComplete?.getPlace().geometry?.location;
        point && props.onClick({ lat: point.lat(), lng: point.lng() });
      }}
    >
      <Input
        type="text"
        placeholder="Unesite traženu adresu..."
        className="shadow"
        style={{
          border: "1px solid grey",
          width: "18em",
          height: "3em",
          padding: "0 12px",
          borderRadius: "3px",
          margin: "0.5em auto",
          position: "absolute",
          zIndex: 10,
          left: 0,
          right: 0,
        }}
      />
    </Autocomplete>
  );
}

export default PlacesInput;
