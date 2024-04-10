import CardBox from "./CardBox";
import CenteredCard from "./CenteredCard";
import Simple from "./Simple";

interface PaginationProps {
  kind: "simple" | "cardbox" | "centeredcard";
  setCurrentPage: (pageNumber: number) => void;
  totalPages: number;
  currentPage: number;
}

export function Pagination({
  kind,
  setCurrentPage,
  totalPages,
  currentPage,
}: PaginationProps) {
  const handlePageChange = (pageNumber: number) => {
    setCurrentPage(pageNumber);
  };

  // Array of page numbers
  const pageNumbers = Array.from(
    { length: totalPages },
    (_, index) => index + 1
  );

  const isFirstPage = currentPage === 1;
  const isLastPage = currentPage === totalPages;

  const props = {
    isFirstPage,
    isLastPage,
    pageNumbers,
    handlePageChange,
    totalPages,
    currentPage,
  };

  switch (kind) {
    case "simple":
      return <Simple {...props} />;
    case "cardbox":
      return <CardBox {...props} />;
    case "centeredcard":
      return <CenteredCard {...props} />;

    default:
      return <Simple {...props} />;
  }
}

export default Pagination;

export interface CommonPaginatonProps {
  isFirstPage: boolean;
  isLastPage: boolean;
  pageNumbers?: Array<number>;
  handlePageChange: (page: number) => void;
  totalPages: number;
  currentPage: number;
}
