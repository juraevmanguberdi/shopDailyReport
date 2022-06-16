/* tslint:disable max-line-length */
import axios from 'axios';
import sinon from 'sinon';

import AssetLiveService from '@/entities/asset-live/asset-live.service';
import { AssetLive } from '@/shared/model/asset-live.model';

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
  describe('AssetLive Service', () => {
    let service: AssetLiveService;
    let elemDefault;

    beforeEach(() => {
      service = new AssetLiveService();
      elemDefault = new AssetLive(123, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 'AAAAAAA', 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', async () => {
        const returnedFromService = Object.assign({}, elemDefault);
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

      it('should create a AssetLive', async () => {
        const returnedFromService = Object.assign(
          {
            id: 123,
          },
          elemDefault
        );
        const expected = Object.assign({}, returnedFromService);

        axiosStub.post.resolves({ data: returnedFromService });
        return service.create({}).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should not create a AssetLive', async () => {
        axiosStub.post.rejects(error);

        return service
          .create({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should update a AssetLive', async () => {
        const returnedFromService = Object.assign(
          {
            totalAssets: 1,
            currentAssets: 1,
            moneyTotal: 1,
            cashShop: 1,
            cashOwner: 1,
            bankAccount: 1,
            goods: 1,
            debitor: 1,
            longTermAssets: 1,
            transport: 1,
            equipment: 1,
            realEstate: 1,
            other: 1,
            code: 'BBBBBB',
            notes: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);
        axiosStub.put.resolves({ data: returnedFromService });

        return service.update(expected).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should not update a AssetLive', async () => {
        axiosStub.put.rejects(error);

        return service
          .update({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should partial update a AssetLive', async () => {
        const patchObject = Object.assign(
          {
            totalAssets: 1,
            cashShop: 1,
            debitor: 1,
            equipment: 1,
            other: 1,
            notes: 'BBBBBB',
          },
          new AssetLive()
        );
        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign({}, returnedFromService);
        axiosStub.patch.resolves({ data: returnedFromService });

        return service.partialUpdate(patchObject).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should not partial update a AssetLive', async () => {
        axiosStub.patch.rejects(error);

        return service
          .partialUpdate({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should return a list of AssetLive', async () => {
        const returnedFromService = Object.assign(
          {
            totalAssets: 1,
            currentAssets: 1,
            moneyTotal: 1,
            cashShop: 1,
            cashOwner: 1,
            bankAccount: 1,
            goods: 1,
            debitor: 1,
            longTermAssets: 1,
            transport: 1,
            equipment: 1,
            realEstate: 1,
            other: 1,
            code: 'BBBBBB',
            notes: 'BBBBBB',
          },
          elemDefault
        );
        const expected = Object.assign({}, returnedFromService);
        axiosStub.get.resolves([returnedFromService]);
        return service.retrieve({ sort: {}, page: 0, size: 10 }).then(res => {
          expect(res).toContainEqual(expected);
        });
      });

      it('should not return a list of AssetLive', async () => {
        axiosStub.get.rejects(error);

        return service
          .retrieve()
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should delete a AssetLive', async () => {
        axiosStub.delete.resolves({ ok: true });
        return service.delete(123).then(res => {
          expect(res.ok).toBeTruthy();
        });
      });

      it('should not delete a AssetLive', async () => {
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
