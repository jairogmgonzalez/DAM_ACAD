import React from "react";
import { useState } from "react";

function MiComponentes() {
    const [count, setCount] = useState(0);

    const increment = () => setCount(count + 1);
    return (
        <div>
        <h1>Mi Componente</h1>
        <p>Contador: {count}</p>
        <button onClick={increment}>Incrementar</button>
        </div>
    );

}

export default MiComponentes;