/* tslint:disable max-line-length */
import axios from 'axios';
import sinon from 'sinon';
import dayjs from 'dayjs';

import { DATE_FORMAT } from '@/shared/date/filters';
import DailyRegistryShopService from '@/entities/daily-registry-shop/daily-registry-shop.service';
import { DailyRegistryShop } from '@/shared/model/daily-registry-shop.model';

const error = {
  response: {
    status: null,
    data: {
      type: null,
    },
  },
};

const axiosStub = {
  get: sinon.stub(axios, 'get'),
  post: sinon.stub(axios, 'post'),
  put: sinon.stub(axios, 'put'),
  patch: sinon.stub(axios, 'patch'),
  delete: sinon.stub(axios, 'delete'),
};

describe('Service Tests', () => {
  describe('DailyRegistryShop Service', () => {
    let service: DailyRegistryShopService;
    let elemDefault;
    let currentDate: Date;

    beforeEach(() => {
      service = new DailyRegistryShopService();
      currentDate = new Date();
      elemDefault = new DailyRegistryShop(123, currentDate, 0, 0, 0, 0, 0, 0, 0, 0, 0, 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', async () => {
        const returnedFromService = Object.assign(
          {
            today: dayjs(currentDate).format(DATE_FORMAT),
          },
          elemDefault
        );
        axiosStub.get.resolves({ data: returnedFromService });

        return service.find(123).then(res => {
          expect(res).toMatchObject(elemDefault);
        });
      });

      it('should not find an element', async () => {
        axiosStub.get.rejects(error);
        return service
          .find(123)
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should create a DailyRegistryShop', async () => {
        const returnedFromService = Object.assign(
          {
            id: 123,
            today: dayjs(currentDate).format(DATE_FORMAT),
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            today: currentDate,
          },
          returnedFromService
        );

        axiosStub.post.resolves({ data: returnedFromService });
        return service.create({}).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should not create a DailyRegistryShop', async () => {
        axiosStub.post.rejects(error);

        return service
          .create({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should update a DailyRegistryShop', async () => {
        const returnedFromService = Object.assign(
          {
            today: dayjs(currentDate).format(DATE_FORMAT),
            sales: 1,
            goods: 1,
            cashShop: 1,
            click: 1,
            terminal: 1,
            debtReturn: 1,
            debtGiven: 1,
            expense: 1,
            balance: 1,
            code: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            today: currentDate,
          },
          returnedFromService
        );
        axiosStub.put.resolves({ data: returnedFromService });

        return service.update(expected).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should not update a DailyRegistryShop', async () => {
        axiosStub.put.rejects(error);

        return service
          .update({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should partial update a DailyRegistryShop', async () => {
        const patchObject = Object.assign(
          {
            cashShop: 1,
            click: 1,
            terminal: 1,
            debtReturn: 1,
            debtGiven: 1,
            expense: 1,
          },
          new DailyRegistryShop()
        );
        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign(
          {
            today: currentDate,
          },
          returnedFromService
        );
        axiosStub.patch.resolves({ data: returnedFromService });

        return service.partialUpdate(patchObject).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should not partial update a DailyRegistryShop', async () => {
        axiosStub.patch.rejects(error);

        return service
          .partialUpdate({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should return a list of DailyRegistryShop', async () => {
        const returnedFromService = Object.assign(
          {
            today: dayjs(currentDate).format(DATE_FORMAT),
            sales: 1,
            goods: 1,
            cashShop: 1,
            click: 1,
            terminal: 1,
            debtReturn: 1,
            debtGiven: 1,
            expense: 1,
            balance: 1,
            code: 'BBBBBB',
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            today: currentDate,
          },
          returnedFromService
        );
        axiosStub.get.resolves([returnedFromService]);
        return service.retrieve({ sort: {}, page: 0, size: 10 }).then(res => {
          expect(res).toContainEqual(expected);
        });
      });

      it('should not return a list of DailyRegistryShop', async () => {
        axiosStub.get.rejects(error);

        return service
          .retrieve()
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should delete a DailyRegistryShop', async () => {
        axiosStub.delete.resolves({ ok: true });
        return service.delete(123).then(res => {
          expect(res.ok).toBeTruthy();
        });
      });

      it('should not delete a DailyRegistryShop', async () => {
        axiosStub.delete.rejects(error);

        return service
          .delete(123)
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });
    });
  });
});
