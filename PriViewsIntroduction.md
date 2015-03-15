# Introduction #

Pri-Views is a tool that allows, given a relation table, to produce views (vertical fragments) over it, in such a way to protect the privacy of possible sensitive information while providing maximal visibility over the data.
Pri-Views is based on a greedy algorithm designed by UNIBG and UNIMI to solve the problem of creating unlinkable fragments in the storage of sensitive attributes. The presented algorithm departs from the use of encryption, while usually in the literature, this kind of problem has been addressed using both fragmentation and encryption.
The project is composed of two applications: the first implements the proposed greedy algorithm (developed in C++), while the second realizes its Graphical User Interface (developed in Java).
Pri-Views project comes within the European project PrimeLife (7th Framework Programme).

This prototype is based on the research presented in the paper:

V. Ciriani, S. De Capitani di Vimercati, S. Foresti, S. Jajodia, S. Paraboschi, and P. Samarati, _[Keep a Few: Outsourcing Data while Maintaining Confidentiality](http://spdp.dti.unimi.it/papers/esorics09.pdf),_ in Proc. of the 14th European Symposium On Research In Computer Security (ESORICS 2009), Saint Malo, France, September 21-25, 2009

a joint work of the universities of Bergamo (Università degli Studi di Bergamo - Dipartimento di Ingegneria dell'Informazione e Metodi Matematici) and Milano (Università degli Studi di Milano - Dipartimento di Tecnologie dell'Informazione).