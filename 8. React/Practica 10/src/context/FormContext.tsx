import { createContext, useState, ReactNode } from "react";

interface UserData {
    name: string;
    age: number;
}

interface FormContextType {
    user: UserData | null;
    setUser: (user: UserData | null) => void;
}

export const FormContext = createContext<FormContextType>({
    user: null,
    setUser: () => {},
});

export function FormProvider({ children }: { children: ReactNode }) {
    const [user, setUser] = useState<UserData | null>(null);
    
    return (
        <FormContext.Provider value={{ user, setUser }}>
            {children}
        </FormContext.Provider>
    );
}