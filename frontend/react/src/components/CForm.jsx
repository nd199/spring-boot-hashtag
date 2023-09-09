import {
    Button,
    Drawer,
    DrawerBody,
    DrawerCloseButton,
    DrawerContent,
    DrawerFooter,
    DrawerHeader,
    DrawerOverlay,
    useDisclosure
} from "@chakra-ui/react";
import CustomerCreationForm from "./CustomerCreationForm.jsx";

const AddIcon = () => "+";

const CForm = ({fetchCustomers}) => {
    const {isOpen, onOpen, onClose} = useDisclosure()
    return (
        <>
            <Button
                onClick={(onOpen)}
                leftIcon={<AddIcon/>}
                colorScheme={"teal"}>
                Create Customer
            </Button>
            <Drawer isOpen={isOpen} onClose={onClose} size={"lg"}>
                <DrawerOverlay/>
                <DrawerContent>
                    <DrawerCloseButton/>
                    <DrawerHeader>Create new Customer</DrawerHeader>

                    <DrawerBody>
                        <CustomerCreationForm
                            fetchCustomers={fetchCustomers}
                        />
                    </DrawerBody>

                    <DrawerFooter>
                        <Button
                            onClick={(onClose)}
                            colorScheme={"teal"}
                        >Close
                        </Button>
                    </DrawerFooter>
                </DrawerContent>
            </Drawer>
        </>
    )
}

export default CForm;


