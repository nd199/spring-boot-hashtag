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
import CreateCustomerForm from "../CreateCustomerForm.jsx";


const CreationForm = ({fetchCustomers}) => {
    const {isOpen, onOpen, onClose} = useDisclosure()
    return <>
        <Button
            onClick={(onOpen)}
            colorScheme={"teal"}
            _hover={{
                transform: 'translateX(4px)'
            }}
            bgColor={"whatsapp.600"}
        >
            Create Customer
        </Button>
        <Drawer isOpen={isOpen} onClose={onClose} size={"lg"}>
            <DrawerOverlay/>
            <DrawerContent>
                <DrawerCloseButton/>
                <DrawerHeader>Create new Customer</DrawerHeader>

                <DrawerBody>
                    <CreateCustomerForm
                        fetchCustomers={fetchCustomers}
                    />
                </DrawerBody>

                <DrawerFooter>
                    <Button
                        onClick={onClose}
                        colorScheme={"teal"}>
                        Close
                    </Button>
                </DrawerFooter>
            </DrawerContent>
        </Drawer>
    </>
}

export default CreationForm;


