import {createStandaloneToast} from '@chakra-ui/toast'

const {toast} = createStandaloneToast()


const alert = (title, description, status) => {
    toast({
        title,
        description,
        status,
        isClosable: true,
        duration: 5000
    })
}


export const successAlert =
    (title, description) => {
        alert(
            title,
            description,
            "success"
        )
    }

export const errorAlert =
    (title, description) => {
        alert(
            title,
            description,
            "error"
        )
    }

