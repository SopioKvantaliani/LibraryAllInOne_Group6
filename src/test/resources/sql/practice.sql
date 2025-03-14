select isbn,B.name,author,C.name,YEAR,B.description
from books B join book_categories C on B.book_category_id = C.id
where B.id = 38053;