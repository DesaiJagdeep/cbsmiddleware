import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { map } from 'rxjs/operators';

import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IKarkhanaVasuliFile, NewKarkhanaVasuliFile } from '../karkhana-vasuli-file.model';

export type PartialUpdateKarkhanaVasuliFile = Partial<IKarkhanaVasuliFile> & Pick<IKarkhanaVasuliFile, 'id'>;

type RestOf<T extends IKarkhanaVasuliFile | NewKarkhanaVasuliFile> = Omit<T, 'fromDate' | 'toDate'> & {
  fromDate?: string | null;
  toDate?: string | null;
};

export type RestKarkhanaVasuliFile = RestOf<IKarkhanaVasuliFile>;

export type NewRestKarkhanaVasuliFile = RestOf<NewKarkhanaVasuliFile>;

export type PartialUpdateRestKarkhanaVasuliFile = RestOf<PartialUpdateKarkhanaVasuliFile>;

export type EntityResponseType = HttpResponse<IKarkhanaVasuliFile>;
export type EntityArrayResponseType = HttpResponse<IKarkhanaVasuliFile[]>;

@Injectable({ providedIn: 'root' })
export class KarkhanaVasuliFileService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/karkhana-vasuli-files');

  constructor(
    protected http: HttpClient,
    protected applicationConfigService: ApplicationConfigService,
  ) {}

  create(karkhanaVasuliFile: NewKarkhanaVasuliFile): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(karkhanaVasuliFile);
    return this.http
      .post<RestKarkhanaVasuliFile>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(karkhanaVasuliFile: IKarkhanaVasuliFile): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(karkhanaVasuliFile);
    return this.http
      .put<RestKarkhanaVasuliFile>(`${this.resourceUrl}/${this.getKarkhanaVasuliFileIdentifier(karkhanaVasuliFile)}`, copy, {
        observe: 'response',
      })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(karkhanaVasuliFile: PartialUpdateKarkhanaVasuliFile): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(karkhanaVasuliFile);
    return this.http
      .patch<RestKarkhanaVasuliFile>(`${this.resourceUrl}/${this.getKarkhanaVasuliFileIdentifier(karkhanaVasuliFile)}`, copy, {
        observe: 'response',
      })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestKarkhanaVasuliFile>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestKarkhanaVasuliFile[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getKarkhanaVasuliFileIdentifier(karkhanaVasuliFile: Pick<IKarkhanaVasuliFile, 'id'>): number {
    return karkhanaVasuliFile.id;
  }

  compareKarkhanaVasuliFile(o1: Pick<IKarkhanaVasuliFile, 'id'> | null, o2: Pick<IKarkhanaVasuliFile, 'id'> | null): boolean {
    return o1 && o2 ? this.getKarkhanaVasuliFileIdentifier(o1) === this.getKarkhanaVasuliFileIdentifier(o2) : o1 === o2;
  }

  addKarkhanaVasuliFileToCollectionIfMissing<Type extends Pick<IKarkhanaVasuliFile, 'id'>>(
    karkhanaVasuliFileCollection: Type[],
    ...karkhanaVasuliFilesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const karkhanaVasuliFiles: Type[] = karkhanaVasuliFilesToCheck.filter(isPresent);
    if (karkhanaVasuliFiles.length > 0) {
      const karkhanaVasuliFileCollectionIdentifiers = karkhanaVasuliFileCollection.map(
        karkhanaVasuliFileItem => this.getKarkhanaVasuliFileIdentifier(karkhanaVasuliFileItem)!,
      );
      const karkhanaVasuliFilesToAdd = karkhanaVasuliFiles.filter(karkhanaVasuliFileItem => {
        const karkhanaVasuliFileIdentifier = this.getKarkhanaVasuliFileIdentifier(karkhanaVasuliFileItem);
        if (karkhanaVasuliFileCollectionIdentifiers.includes(karkhanaVasuliFileIdentifier)) {
          return false;
        }
        karkhanaVasuliFileCollectionIdentifiers.push(karkhanaVasuliFileIdentifier);
        return true;
      });
      return [...karkhanaVasuliFilesToAdd, ...karkhanaVasuliFileCollection];
    }
    return karkhanaVasuliFileCollection;
  }

  protected convertDateFromClient<T extends IKarkhanaVasuliFile | NewKarkhanaVasuliFile | PartialUpdateKarkhanaVasuliFile>(
    karkhanaVasuliFile: T,
  ): RestOf<T> {
    return {
      ...karkhanaVasuliFile,
      fromDate: karkhanaVasuliFile.fromDate?.toJSON() ?? null,
      toDate: karkhanaVasuliFile.toDate?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restKarkhanaVasuliFile: RestKarkhanaVasuliFile): IKarkhanaVasuliFile {
    return {
      ...restKarkhanaVasuliFile,
      fromDate: restKarkhanaVasuliFile.fromDate ? dayjs(restKarkhanaVasuliFile.fromDate) : undefined,
      toDate: restKarkhanaVasuliFile.toDate ? dayjs(restKarkhanaVasuliFile.toDate) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestKarkhanaVasuliFile>): HttpResponse<IKarkhanaVasuliFile> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestKarkhanaVasuliFile[]>): HttpResponse<IKarkhanaVasuliFile[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
