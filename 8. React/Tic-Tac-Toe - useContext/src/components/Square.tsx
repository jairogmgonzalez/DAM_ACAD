import Button from "@mui/material/Button";
import CloseIcon from "@mui/icons-material/Close";
import RadioButtonUncheckedIcon from "@mui/icons-material/RadioButtonUnchecked";

type SquareProps = {
    value: "X" | "O" | null;
    handleClick: () => void;
}

function Square({ value, handleClick }: SquareProps) {
    return (
        <Button
            onClick={handleClick}
            variant="outlined"
            sx={{
                width: "80px",
                height: "80px",
                display: "flex",
                alignItems: "center",
                justifyContent: "center",
                fontSize: "24px",
            }}
        >
            {value === "X" && <CloseIcon fontSize="large" />}
            {value === "O" && <RadioButtonUncheckedIcon fontSize="large" />}
        </Button>
    );
}

export default Square;