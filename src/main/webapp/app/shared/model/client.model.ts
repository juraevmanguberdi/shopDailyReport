export interface IClient {
  id?: number;
  name?: string;
  surName?: string;
  debtAmount?: number | null;
  address?: string | null;
  phone?: string;
  notes?: string | null;
  createdDate?: Date | null;
  code?: string | null;
}

export class Client implements IClient {
  constructor(
    public id?: number,
    public name?: string,
    public surName?: string,
    public debtAmount?: number | null,
    public address?: string | null,
    public phone?: string,
    public notes?: string | null,
    public createdDate?: Date | null,
    public code?: string | null
  ) {}
}
